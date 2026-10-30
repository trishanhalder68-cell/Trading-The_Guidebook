const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// --- SECURITY BOUNDS TESTS ---

test("Unauthenticated user: cannot read users or transactions", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("transactions").get());
});

test("Authenticated user: can create and read their own user profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const profileDoc = aliceDb.collection("users").doc(ALICE_UID);
  
  await assertSucceeds(
    profileDoc.set({
      userId: ALICE_UID,
      displayName: "Alice Sovereign",
      isVip: true,
      monthlyTarget: 50000,
      annualTarget: 600000,
      unlockedLevels: [1, 2],
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );

  await assertSucceeds(profileDoc.get());
});

test("Authenticated user: cannot read or write another user's profile", async () => {
  // Alice attempts to read or write Bob's profile
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("users").doc(BOB_UID).get());
  await assertFails(
    aliceDb.collection("users").doc(BOB_UID).set({
      userId: BOB_UID,
      displayName: "Bob Trader",
      isVip: false,
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );
});

test("Authenticated user: can create and read their own transactions", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const txRef = aliceDb.collection("users").doc(ALICE_UID).collection("transactions").doc("tx_001");

  await assertSucceeds(
    txRef.set({
      userId: ALICE_UID,
      title: "Trading Dividend",
      amount: 15000,
      type: "INCOME",
      category: "Stock Market",
      date: "2026-10-30",
      createdAt: new Date(),
      updatedAt: new Date(),
    })
  );

  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).collection("transactions").get());
});

test("Authenticated user: cannot read another user's transactions", async () => {
  // Seed Bob's transaction
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(BOB_UID).collection("transactions").doc("bob_tx").set({
      userId: BOB_UID,
      title: "Private Dividend",
      amount: 50000,
      type: "INCOME",
      category: "Stocks",
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("users").doc(BOB_UID).collection("transactions").doc("bob_tx").get());
  await assertFails(aliceDb.collection("users").doc(BOB_UID).collection("transactions").get());
});

test("Update test: cannot alter createdAt on transaction", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const txRef = aliceDb.collection("users").doc(ALICE_UID).collection("transactions").doc("tx_002");
  const now = new Date();

  await assertSucceeds(
    txRef.set({
      userId: ALICE_UID,
      title: "Options Profit",
      amount: 12000,
      type: "INCOME",
      category: "Options",
      createdAt: now,
      updatedAt: now,
    })
  );

  // Attempt to alter createdAt
  await assertFails(
    txRef.update({
      title: "Updated Options Profit",
      createdAt: new Date(now.getTime() - 10000),
      updatedAt: new Date(),
    })
  );
});
