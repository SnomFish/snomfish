import Background from "../../components/background/Background";
import Navbar from "../../components/navbar/Navbar";

import "./Home.css";
import "../../index.css";

export default function home() {


	return (<div>
		<Navbar />
		<Background />
		<div className="page-container">
			<div className="scroll-container">
				<div className="scroll-section play">
					play
				</div>
				<div className="scroll-section about">
					about
				</div>
			</div>
		</div>
			
	</div>);
}