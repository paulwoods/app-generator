import {AppBar, Box, Button, IconButton, Paper, Tab, Tabs, Toolbar, Typography} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import {RequestForm} from "../components/RequestForm.tsx";
import axios from "axios";
import type {AppRequestType, FileBuilderNameType, GenerateResultsType} from "../../types.ts";
import {type SyntheticEvent, useEffect, useState} from "react";
import {CodeComponent} from "../components/CodeComponent.tsx";

export const HomeIndexPage = () => {

    const [results, setResults] = useState<GenerateResultsType>();
    const [names, setNames] = useState<FileBuilderNameType[]>([]);
    const [tab, setTab] = useState("FORM");

    useEffect(() => {
        axios.get<FileBuilderNameType[]>("/backend/v1/file-builder-name")
            .then(response => response.data)
            .then(setNames)
            .catch(console.error)
    }, [])

    const handleGenerate = (appRequest: AppRequestType) => {
        axios.post<GenerateResultsType>("/backend/v1/generate", appRequest)
            .then(response => response.data)
            .then(setResults)
            .catch(console.error)
    };

    const handleChangeTab = (_event: SyntheticEvent, newValue: string) => {
        setTab(newValue);
    };

    const code = results && results.codes.find(c => c.name === tab)

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

        <Box sx={{width: "100%"}}>

            <Tabs value={tab} onChange={handleChangeTab}>
                <Tab label="FORM" value="FORM"/>
                {results && names.map(fbn =>
                    <Tab key={fbn.name} label={fbn.name} value={fbn.name}/>
                )}
            </Tabs>

        </Box>

        <Paper sx={{m: 3}}>
            {tab === "FORM" && <Box sx={{p: 4, width: "100%"}}>
              <RequestForm storage="home.request-form" onGenerate={handleGenerate}/>
            </Box>}

            {code && <CodeComponent code={code}/>}

        </Paper>

    </Box>

}