import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import {BrowserRouter, Route, Routes} from "react-router";
import {HomeIndexPage} from "./home/HomeIndexPage.tsx";
import {CssBaseline} from "@mui/material";

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <CssBaseline/>
        <BrowserRouter>
            <Routes>
                <Route index element={<HomeIndexPage/>}/>
            </Routes>
        </BrowserRouter>
    </StrictMode>
)
