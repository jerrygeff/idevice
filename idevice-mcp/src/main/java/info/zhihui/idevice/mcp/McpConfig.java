package info.zhihui.idevice.mcp;

import info.zhihui.idevice.mcp.access.AccessMCPService;
import info.zhihui.idevice.mcp.camera.CameraMcpService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jerryge
 */
@Configuration
public class McpConfig {
    @Bean
    public ToolCallbackProvider accessTools(AccessMCPService accessMCPService) {
        return MethodToolCallbackProvider.builder().toolObjects(accessMCPService).build();
    }

    @Bean
    public ToolCallbackProvider cameraTools(CameraMcpService cameraMcpService) {
        return MethodToolCallbackProvider.builder().toolObjects(cameraMcpService).build();
    }
}
