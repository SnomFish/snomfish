import { useState } from "react";

import "../../index.css";
import "../../Themes.css";
import "./Navbar.css";

export default function ThemeDropdown() {
	const [open, setOpen] = useState(false);
	const toggle = () => setOpen((prev) => !prev);

	function setTheme(theme: string) {
		const html = document.documentElement;
		const validThemes = ['light', 'dark'];

		if (!validThemes.includes(theme)) {
			theme = window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light";
		}
		html.dataset.theme = theme;
	}

	return (<div className="theme-container">
		<div onClick={() => toggle()} className="theme-trigger">Theme</div>
		{open && <div className="theme-menu">
			<div className="theme-item light"   onClick={() => setTheme("light")}>light</div>
			<div className="theme-item dark"    onClick={() => setTheme("dark")}>dark</div>
			<div className="theme-item system"  onClick={() => setTheme("system")}>system</div>
		</div>}
	</div>);
}