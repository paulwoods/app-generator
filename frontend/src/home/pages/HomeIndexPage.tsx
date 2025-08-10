import {AppBar, Box, Button, IconButton, Toolbar, Typography} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import {AppRequestForm} from "../components/AppRequestForm.tsx";
import axios from "axios";
import type {AppRequest} from "../../types.ts";

export const HomeIndexPage = () => {

    const handleGenerate = (appRequest: AppRequest) => {
        axios.post("/backend/v1/generate", appRequest)
            .then(response => response.data)
            .then(console.log)
            .catch(console.error)
    };

    return <Box sx={{flexGrow: 1}}>
        <AppBar position="static" enableColorOnDark>
            <Toolbar>
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    sx={{mr: 2}}
                >
                    <MenuIcon/>
                </IconButton>
                <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                    App Generator
                </Typography>
                <Button color="inherit">Login</Button>
            </Toolbar>
        </AppBar>

        <Box sx={{ml: 3, width: "25vw"}}>
            <Box>
                <h1>Form</h1>
            </Box>

            <AppRequestForm storage="home.request-form" onGenerate={handleGenerate}/>

        </Box>

    </Box>

}