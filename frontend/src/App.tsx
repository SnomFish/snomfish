import { useEffect } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/home/Home";

function App() {


  function setTheme() {
    const savedTheme = localStorage.getItem("theme");
    const systemTheme = window.matchMedia("(prefers-color-scheme: dark)").matches
      ? "dark"
      : "light";

    const theme = savedTheme || systemTheme;
    document.documentElement.setAttribute("data-theme", theme);
  }
  useEffect(() => {
    setTheme();
  }, []);
  
  
  return <div>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
      </Routes>
    </BrowserRouter>
  </div>
}

export default App
