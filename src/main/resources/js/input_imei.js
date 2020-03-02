import ApplyButtonOptions from '/js/button_apply_options.js';

'use strict';
const e = React.createElement;

class IMEIInput extends React.Component {
    constructor() {
        super();
        this.state = {IMEI: '', validate: false};
        this.inputOnChange = this.inputOnChange.bind(this);
    }

    inputOnChange(e) {
        if(e.target.value != '')
            this.setState({IMEI: e.target.value, validate: true});
        else
            this.setState({IMEI: '', validate: false});
    }

    render() {
        return e('div', {id: 'addBlInputDiv'},
                    e('label', {id: 'addBlLabel'}, 'IMEI: '),
                    e('input', {id: 'imeiInput', type:'text', onChange: this.inputOnChange, value: this.state.IMEI}, null),
                    e(ApplyButtonOptions, {id: 'btnApplyAddBl', disabled: !this.state.validate}));
    }
}

export default IMEIInput;