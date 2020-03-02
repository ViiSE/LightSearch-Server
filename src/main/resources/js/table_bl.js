import ApplyButtonTables from '/js/button_apply_tables.js';

'use strict';
const e = React.createElement;

var isErrorShowBl = true;
var isRefreshBl = true;
var currentIMEIBl = '';
var actionsLength = 0;

class BlacklistTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {blacklist: [], actions: [], disabled: true};

        this.mouseOverSelect = this.mouseOverSelect.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.getActiveActions = this.getActiveActions.bind(this);
        this.refreshTable = this.refreshTable.bind(this);
        this.updatedatahandler = this.updatedatahandler.bind(this);
    }

    refreshTable() {
        var activeActions = this.getActiveActions();
        var newDisabled = activeActions.length == 0 ? true : false;
        if(isRefreshBl) {
            fetch('/commands/type/admin?command=blacklist')
                .then(response => response.json())
                .then((result) => {console.log('BLLLLLLLLL: ' + result.blacklist); var bl = result.blacklist; this.setState({blacklist: bl, actions: activeActions, disabled: newDisabled});},
                      (error) => {if(isErrorShowBl){isErrorShowBl = false; nfcErr({title: 'Error', message: error.message});} if(error.message.includes('NetworkError')) {isRefreshBl = false;}});
        }
    }

    componentDidMount() {
        this.refreshTable();
    }

    getActiveActions() {
        var activeActions = [];
        var newDisabled = false;
        for(var i = 0; i < this.state.actions.length; i++)
            if(!this.state.actions[i].isProcessed)
                activeActions.push(this.state.actions[i]);
        return activeActions;
    }

    updatedatahandler() {
        console.log(JSON.stringify(this.getActiveActions()));
        console.log("I AM UPDATE");
        this.refreshTable();
    }

    mouseOverSelect(event) {
        currentIMEIBl = this.state.blacklist[event.currentTarget.rowIndex-1];
    }

    handleSelect(event) {
        var actionVal = event.currentTarget.value;
        var newActions = [];
        var newDisabled = false;
        if(actionVal != 'none') {
            var action = new Object();
            action.command = actionVal;
            action.IMEI = currentIMEIBl;
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
            newActions = this.state.actions.filter(action => action.IMEI != currentIMEIBl);
            if(newActions.length == 0)
                newDisabled = true;
        }
        this.setState({actions: newActions, disabled: newDisabled});
        currentIMEIBl = '';
        console.log(JSON.stringify(this.getActiveActions()));
        console.log("I AM SELECT");
    }

    renderTableData() {
        return this.state.blacklist.map(IMEI => {
            return e('tr', {key: IMEI, onMouseOver: this.mouseOverSelect},
                        e('td', null, IMEI),
                        e('td', null,
                            e('select', {onChange:this.handleSelect},
                                e('option', {value:'none'}, "None"),
                                e('option', {value:'delBlacklist'}, "Delete from the Blacklist"))));
        });
    }

    render() {
        return e('div', {id: 'divBlTable'},
                    e('h2', {id: 'blTitle'}, 'Blacklist'),
                    e('table', {align:'center', id:'blacklist'},
                        e('thead', null,
                            e('tr', null,
                                e('th', {key: 'IMEI'}, 'IMEI'),
                                e('th', {key: 'actions'}, 'Actions'))),
                        e('tbody', null,
                            this.renderTableData())),
                    e(ApplyButtonTables, {id: 'btnApplyBl', actions: this.state.actions, disabled: this.state.disabled, ishandler: 'true', updatedatahandler: this.updatedatahandler}));
    }
}

export default BlacklistTable;
