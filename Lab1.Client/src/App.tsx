import AppRoutes from "./AppRoutes.tsx";
import Layout from "./Layout.tsx";
import { Route, Routes } from 'react-router-dom';

function App() {


    return (
        <Layout>
            <Routes>
                {AppRoutes.map((route, index) => {
                    const { element, ...rest } = route;
                    return <Route key={index} {...rest} element={element} />;
                })}
            </Routes>
        </Layout>
    );
}

export default App
