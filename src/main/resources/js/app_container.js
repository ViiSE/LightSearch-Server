import TablesContainer from '/js/tables_container.js';
import OptionsContainer from '/js/options_container.js';

'use strict';
const e = React.createElement;

class AppContainer extends React.Component {
    constructor() {
        super();
    }

    render() {
        return e('div', {id: 'app'},
                    e(TablesContainer, null),
                    e(OptionsContainer, null));
    }
}

export default AppContainer;