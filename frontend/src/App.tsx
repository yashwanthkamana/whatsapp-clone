import React, { useEffect } from "react";
import { RecoilRoot } from "recoil";

function App() {
  let ws: WebSocket | null = null;
  let textEncoder = new TextEncoder();
  let textDecoder = new TextDecoder();
  useEffect(() => {
    ws = new WebSocket("ws://localhost:8080/chat");
    ws.onopen = () => console.log("connected");

    return () => {
      ws!.onclose = () => console.log("disconnected");
    };
  }, []);
  const sendMessage = () => {
    console.log(textEncoder.encode());

    ws?.send(JSON.stringify({ name: "yash", message: "hi" }));
  };
  return (
    <RecoilRoot>
      <div className="text-3xl">hello</div>
      <button onClick={sendMessage}>send</button>
    </RecoilRoot>
  );
}

export default App;
