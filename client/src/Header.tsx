import {AppBar, Toolbar, Typography} from "@material-ui/core"
import React from "react"

const Header: React.FC = () => {
    return (
        <AppBar position="sticky">
            <Toolbar>
                <Typography variant="h6">
                    Simple TodoList Application
                </Typography>
            </Toolbar>
        </AppBar>
    )
}

export default Header
