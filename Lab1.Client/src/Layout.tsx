import {Box} from "@mui/material";
import React from "react";
import AppDrawer from "./components/AppDrawer.tsx";

const Layout = ({ children }: {children: React.ReactNode} ) => {
    return (
        <Box sx={{ display: "flex" }}>
            <AppDrawer />
            <Box style={{ margin: "5pt"}}>
                {children}
            </Box>
        </Box>
    );
}

export default Layout;