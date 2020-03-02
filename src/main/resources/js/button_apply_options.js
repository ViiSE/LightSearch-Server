'use strict';
const e = React.createElement;

var isErrorShowABO = true;

class ApplyButtonOptions extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return e('button', {id: this.props.id, disabled: this.props.validate}, 'Apply');
    }
}

export default ApplyButtonOptions;