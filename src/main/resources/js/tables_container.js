import ClientsTable from '/js/table_cl.js';
import BlacklistTable from '/js/table_bl.js';

'use strict';
const e = React.createElement;

class TablesContainer extends React.Component {
    constructor() {
        super();
    }

    render() {
        return e('div', {id: 'tablesContainer'},
                    e('label', {id:'cTitle'}, 'Tables'),
                    e(ClientsTable, null),
                    e(BlacklistTable, null));
    }
}

export default TablesContainer;
