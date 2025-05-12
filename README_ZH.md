# idevice - AI友好的智能设备系统集成平台

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green.svg)

[English Version](README.md)

## 项目介绍

idevice 是一个AI友好的智能设备系统集成平台。基于Spring Boot实现了MCP服务和REST服务。提供设备管理、数据采集、事件处理等功能，方便对不同区域的设备进行统一管理。
支持大华、海康等主流设备的统一接入和管理。

### 动机
在实际项目中经常需要对接第三方安防、能耗设备。他们实现的功能类似但提供的接口各不相同。

同时还需要对新设备、已有设备进行统一管理。例如：产业园或小区的一期、二期、三期设备进行统一管理。

所以我开发了idevice。通过对不同设备厂商的sdk进行对接，在此之上进行统一封装，提供统一的数据接口。
从而减少重复开发，降低开发成本，同时构建起可扩展的设备管理架构。


## 支持的功能模块

- ✅ 人员模块
- ✅ 门禁模块
- ✅ 视频模块 
- ⏳ 车辆模块 
- ⏳ 访客模块
- ⏳ 能耗模块

## 项目结构

| 模块名                             | 描述                                             |
|---------------------------------|------------------------------------------------|
| idevice-bootstrap               | 应用程序启动模块，包含主应用程序入口和配置                          |
| idevice-common                  | 公共模块，提供整个项目共享的工具类、常量和基础功能                      |
| idevice-components              | 组件模块，包含可复用的独立于业务的功能组件                          |
| idevice-core                    | 核心模块，包含系统核心业务逻辑和领域模型                           |
| idevice-feature                 | 功能特性模块，在核心模块之上提供统一的业务特性处理，例如：命令处理、设备心跳、设备数据同步等 |
| idevice-web                     | Web模块，处理HTTP请求、响应和前端交互                         |
| idevice-mcp                     | MCP协议实现，为AI提供数据接口                              |

详细的项目结构和目录说明请参阅 [项目结构说明文档](doc/project-structure.md)

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8.1+
- MySQL（可选）
- Redis（可选）

### 源码构建
#### 解决artemis-http-client包无法引入
```shell
mvn install:install-file -Dfile=$PATH_TO_PROJECT/lib/artemis-http-client-1.1.13.RELEASE.jar -DgroupId=com.hikvision.ga -DartifactId=artemis-http-client -Dversion=1.1.13.RELEASE -Dpackaging=jar
```

### API 文档

应用启动后，访问以下地址查看 API 文档：

- Knife4j UI: http://your-ip:port/doc.html

## 项目约定

详细的编码规范和项目约定请参阅 [编码规范说明文档](doc/coding_standards_explanation.md)

## 如何对接新的第三方平台
1. 根据新平台提供的文档，在idevice-core-sdk中添加新的sdk模块
2. 使用上一步新增的sdk，实现idevice-core-module/concrete/下对应的接口
3. 通过DeviceModuleConfigService设置对应区域下该平台的配置信息

## 版权许可

此项目使用 MIT 许可证 - 详情请参阅 [LICENSE](LICENSE) 文件

## 联系方式

- 邮箱：jerrygeff@163.com