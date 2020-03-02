import ApplyButtonOptions from '/js/button_apply_options.js';

'use strict';
const e = React.createElement;

class ClientTimeoutInput extends React.Component {
    constructor() {
        super();
        this.state = {timeout: '', validate: false};
        this.inputOnChange = this.inputOnChange.bind(this);
    }

    inputOnChange(e) {
        if(e.target.value != '')
            this.setState({timeout: e.target.value, validate: true});
        else
            this.setState({timeout: '', validate: false});
    }

    render() {
        return e('div', {id: 'toutClInputDiv'},
                    e('label', {id: 'toutClLabel'}, 'Value: '),
                    e('input', {id: 'toutInput', type:'text', onChange: this.inputOnChange, value: this.state.timeout}, null),
                    e(ApplyButtonOptions, {id: 'btnApplyToutCl', disabled: !this.state.validate}));
    }
}

export default ClientTimeoutInput;