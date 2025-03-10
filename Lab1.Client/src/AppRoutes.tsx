import Home from "./components/Home.tsx";
import PostComponent from "./components/PostComponent.tsx";


const AppRoutes = [
    {
        path: '/',
        element: <Home />
    },
    {
        path: '/post',
        element: <PostComponent/>
    }
];

export default AppRoutes;