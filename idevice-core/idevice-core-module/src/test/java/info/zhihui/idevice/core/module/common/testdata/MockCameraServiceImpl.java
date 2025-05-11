package info.zhihui.idevice.core.module.common.testdata;

import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

/**
 * @author jerryge
 */
public class MockCameraServiceImpl implements TestCameraService {
    @Override
    public String getModuleName() {
        return null;
    }

    @Override
    public <T extends EventBo, E extends EventParser<T>> E getEventParser(Class<T> eventTypeClass) {
        return null;
    }
}
