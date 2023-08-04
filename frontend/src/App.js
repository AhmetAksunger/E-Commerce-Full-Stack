import logo from "./logo.svg";
import "./App.css";
import "semantic-ui-css/semantic.min.css";
import Navi from "./layouts/Navi";
import { Route } from "react-router-dom/cjs/react-router-dom.min";
import SignUp from "./pages/SignUp";

function App() {
  return (
    <div className="App">
      <Navi />
      <Route exact path="/sign-up" component={SignUp}/>
    </div>
  );
}

export default App;
