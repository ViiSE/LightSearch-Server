package lightsearch.server.entity;

import lightsearch.server.constants.LogTypesConstants;
import lightsearch.server.data.AdminCommandResultWithLogsDTO;
import lightsearch.server.data.AdminCommandSimpleResultDTO;
import lightsearch.server.data.LogDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("adminCommandResultWithLogs")
@Scope("prototype")
public class AdminCommandResultWithLogsImpl implements AdminCommandResult {

    private final AdminCommandResult admCmdRes;
    private final Map<String, List<String>> logMap;

    public AdminCommandResultWithLogsImpl(AdminCommandResult admCmdRes, Map<String, List<String>> logMap) {
        this.admCmdRes = admCmdRes;
        this.logMap = logMap;
    }

    @Override
    public Object formForSend() {
        AdminCommandSimpleResultDTO admCmdResDTO = (AdminCommandSimpleResultDTO) admCmdRes.formForSend();

        List<LogDTO> logDTOList = new ArrayList<>();
        logMap.forEach((date, logs) -> {
            for(String log: logs) {
                String type = log.contains(LogMessageTypeEnum.INFO.stringValue()) ?
                        LogMessageTypeEnum.INFO.stringValue() :
                        LogMessageTypeEnum.ERROR.stringValue();

                LogDTO logDTO = new LogDTO();
                logDTO.setDate(date);
                logDTO.setMsg(log);
                logDTO.setType(type);

                logDTOList.add(logDTO);
            }
        });

        AdminCommandResultWithLogsDTO resDTO = new AdminCommandResultWithLogsDTO(
                admCmdResDTO.getIsDone(),
                admCmdResDTO.getMessage());
        resDTO.setLogs(logDTOList);
        resDTO.setTypes(LogTypesConstants.listOf());
        return resDTO;
    }

    @Override
    public boolean isDone() {
        return admCmdRes.isDone();
    }

    @Override
    public String message() {
        return admCmdRes.message();
    }
}
