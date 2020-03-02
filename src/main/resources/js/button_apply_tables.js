'use strict';
const e = React.createElement;

var actions = [];

class ApplyButtonTables extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        actions = this.props.actions;
        for(var i = 0; i < actions.length; i++) {
            var action = actions[i];
            fetch('/commands/type/admin', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json;charset=utf-8'},
                            body: JSON.stringify(action)
                        })
                        .then(response => response.json())
                        .then((result) => {var rm = 'isDone: ' + result.isDone + '\nmessage: ' + result.message; if(!result.isDone) {nfcWarn({title: 'Warning', message: rm});} else {nfcSuccess({title: 'Success', message: rm});}},
                              (error) => {nfcErr({title: 'Error', message: error.message});});
            actions[i].isProcessed = true;
        }
        if(this.props.ishandler == 'true')
            this.props.updatedatahandler.apply();
    }

    render() {
        return e('button', {id: this.props.id, disabled: this.props.disabled, actions: this.props.actions, onClick: this.handleClick, ishandler: this.props.ishandler, updatedatahandler: this.props.updatedatahandler}, 'Apply');
    }
}

export default ApplyButtonTables;
