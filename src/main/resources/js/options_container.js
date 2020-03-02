import IMEIInput from '/js/input_imei.js';
import ClientTimeoutInput from '/js/input_tout_cl.js';

'use strict';
const e = React.createElement;

class OptionsContainer extends React.Component {
    constructor() {
        super();
    }

    render() {
        return e('div', {id: 'optionsContainer'},
                    e('label', {id: 'cTitle'}, 'Options'),
                        e('div', {id: 'addBl'},
                            e('label', {id: 'addBlOpt'}, 'Add to the Blacklist'),
                            e(IMEIInput, null)),
                        e('div', {id:'toutCl'},
                            e('label', {id: 'toutClOpt'}, 'Client Timeout')),
                            e(ClientTimeoutInput, null));
    }
}

export default OptionsContainer;
