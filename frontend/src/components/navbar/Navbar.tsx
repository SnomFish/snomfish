import "../../index.css";
import "../../Themes.css";
import "./Navbar.css";

import ThemeDropdown from "./ThemeDropdown";

export default function Navbar() {

  return (<div>
    <div className="container-main">
      <div className="container-width">
        <div className="container-left">
          left
        </div>
        <div className="container-right">
        	<ThemeDropdown />
        </div>
      </div>
    </div>
  </div>);
}