import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const API_URL = "http://localhost:8080/Lab1.Server-1.0-SNAPSHOT/api/hello-world";

    const [message, setMessage] = useState("");

    useEffect(() => {
        axios.get(API_URL)
            .then((response) => {
                setMessage(response.data);
            })
            .catch((error) => {
                console.error("Error fetching data:", error);
                setMessage("Error fetching message");
            });
    }, []);

    console.log(message)

    return (
    <p>{message}</p>
    )
}

export default App
