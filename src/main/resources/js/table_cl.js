import ApplyButtonTables from '/js/button_apply_tables.js';

'use strict';
const e = React.createElement;

var isErrorShowCl = true;
var isRefreshCl = true;
var currentIMEICl = '';

class ClientsTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {clients: [], actions: [], disabled: true};

        this.mouseOverSelect = this.mouseOverSelect.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    componentDidMount() {
        this.timerID = setInterval(() => this.refreshTable(), 1000);
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    refreshTable() {
        if(isRefreshCl) {
            fetch('/commands/type/admin?command=clientList')
    		    .then(response => response.json())
    		    .then((result) => {this.setState({clients: result.clients})},
    		          (error) => {if(isErrorShowCl){isErrorShowCl = false; nfcErr({title: 'Error', message: error.message});} if(error.message.includes('NetworkError')) {isRefreshCl = false;}});
            this.setState(state => {
                if(state.actions.length != 0) {
                    var activeActions = [];
                    var newDisabled = false;
                    for(var i = 0; i < state.actions.length; i++)
                    if(!state.actions[i].isProcessed)
                        activeActions.push(state.actions[i]);
                    if(activeActions.length == 0)
                        newDisabled = true;
                    return {actions: activeActions, disabled: newDisabled}}});
        }
    }

    mouseOverSelect(event) {
        currentIMEICl = this.state.clients[event.currentTarget.rowIndex-1].IMEI;
    }

    handleSelect(event) {
        var actionVal = event.currentTarget.value;
        var newActions = [];
        var newDisabled = false;
        if(actionVal != 'none') {
            var action = new Object();
            action.command = actionVal;
            action.IMEI = currentIMEICl;
            action.isProcessed = false;
            if(this.state.actions.find(_action => _action.IMEI == action.IMEI) == undefined) {
                newActions = this.state.actions;
                newActions.push(action);
            } else {
                var foundIndex = state.actions.findIndex(_action => _action.IMEI == action.IMEI);
                newActions = this.state.actions;
                if(foundIndex != -1)
                    newActions[foundIndex] = action;
            }
        } else {
            newActions = this.state.actions.filter(action => action.IMEI != currentIMEICl);
            if(newActions.length == 0)
                newDisabled = true;
        }
        this.setState({actions: newActions, disabled: newDisabled});
        currentIMEICl = '';
    }

    renderTableData() {
        return this.state.clients.map(client => {
            const {IMEI, username, timeout} = client;
            return e('tr', {key: IMEI, onMouseOver: this.mouseOverSelect},
                        e('td', null, IMEI),
                        e('td', null, username),
                        e('td', null, timeout),
                        e('td', null,
                            e('select', {onChange:this.handleSelect},
                                e('option', {value:'none'}, "None"),
                                e('option', {value:'kick'}, "Kick"),
                                e('option', {value:'addBlacklist'}, "Add to the Blacklist"))));
        });
    }

    render() {
        return e('div', {id: 'divClTable'},
                    e('h2', {id: 'clTitle'}, 'Clients'),
                    e('table', {align:'center', id:'clients'},
                        e('thead', null,
                            e('tr', null,
                                e('th', {key: 'IMEI'}, 'IMEI'),
                                e('th', {key: 'username'}, 'Username'),
                                e('th', {key: 'timeout'}, 'Timeout'),
                                e('th', {key: 'actions'}, 'Actions'))),
                        e('tbody', null,
                            this.renderTableData())),
                    e(ApplyButtonTables, {id: 'btnApplyClients', actions: this.state.actions, disabled: this.state.disabled, ishandler: 'false', updatedatahandler: null}));
    }
}

export default ClientsTable;
