// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider } from "firebase/auth";

const firebaseConfig = {
    apiKey: "AIzaSyBNmJqP1ZfcPAwQcolt7DtWztuhh6Yhv6w",
    authDomain: "whatsapp-clone-2c061.firebaseapp.com",
    projectId: "whatsapp-clone-2c061",
    storageBucket: "whatsapp-clone-2c061.appspot.com",
    messagingSenderId: "877351847409",
    appId: "1:877351847409:web:a1a861f36155fa12fe7050"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth = getAuth(app)
export const provider = new GoogleAuthProvider();
