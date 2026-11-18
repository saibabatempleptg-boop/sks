const { initializeTestEnvironment, assertFails, assertSucceeds } = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

const PROJECT_ID = "demo-no-project";
let testEnv;

before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules: fs.readFileSync("firestore.rules", "utf8"),
      host: "127.0.0.1",
      port: 8085,
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

test("unauthenticated user cannot read or write reservations", async () => {
  const unauthedDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthedDb.collection("users").doc("user1").collection("reservations").get());
  await assertFails(unauthedDb.collection("users").doc("user1").collection("reservations").add({
    userId: "user1",
    sevaId: "sri_krishna_special_puja",
    sevaTitle: "Special Puja",
    devoteeName: "Devotee",
    devoteePhone: "9876543210",
    gotram: "Kashyapa",
    preferredDate: "2026-11-15",
    createdAt: new Date(),
    updatedAt: new Date()
  }));
});

test("authenticated user can create and read their own reservation", async () => {
  const user1Db = testEnv.authenticatedContext("user1").firestore();
  const resRef = user1Db.collection("users").doc("user1").collection("reservations").doc("res1");
  await assertSucceeds(resRef.set({
    userId: "user1",
    sevaId: "sri_krishna_special_puja",
    sevaTitle: "Special Puja",
    devoteeName: "Devotee One",
    devoteePhone: "9876543210",
    gotram: "Kashyapa",
    preferredDate: "2026-11-15",
    createdAt: new Date(),
    updatedAt: new Date()
  }));
  await assertSucceeds(resRef.get());
});

test("authenticated user cannot access another user's reservations", async () => {
  const user1Db = testEnv.authenticatedContext("user1").firestore();
  const resRef = user1Db.collection("users").doc("user1").collection("reservations").doc("res1");
  await assertSucceeds(resRef.set({
    userId: "user1",
    sevaId: "daily_midday_nivedan",
    sevaTitle: "Daily Midday Nivedan",
    devoteeName: "Devotee One",
    devoteePhone: "9876543210",
    gotram: "Kashyapa",
    preferredDate: "2026-11-15",
    createdAt: new Date(),
    updatedAt: new Date()
  }));

  const user2Db = testEnv.authenticatedContext("user2").firestore();
  await assertFails(user2Db.collection("users").doc("user1").collection("reservations").doc("res1").get());
  await assertFails(user2Db.collection("users").doc("user1").collection("reservations").doc("res2").set({
    userId: "user1",
    sevaId: "daily_midday_nivedan",
    sevaTitle: "Daily Midday Nivedan",
    devoteeName: "Devotee Two",
    devoteePhone: "9876543210",
    gotram: "Bharadwaja",
    preferredDate: "2026-11-15",
    createdAt: new Date(),
    updatedAt: new Date()
  }));
});
