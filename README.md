# idevice - AI-Friendly Intelligent Device Integration Platform

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green.svg)

[中文版](README_ZH.md)

## Project Introduction

idevice is an AI-friendly intelligent device integration platform. It implements MCP services and REST services based on Spring Boot, providing device management, data collection, event processing, and other functionalities to facilitate unified management of devices across different regions.
It supports the integration and management of mainstream devices from manufacturers such as Dahua and Hikvision.

### Motivation
In real-world projects, there is often a need to connect with third-party security and energy consumption devices. These devices perform similar functions but provide different interfaces.

At the same time, there is a need to manage both new and existing devices in a unified way. For example, managing devices from phase one, two, and three of an industrial park or residential area.

Therefore, I developed idevice. By interfacing with SDKs from different device manufacturers and providing a unified encapsulation on top, it offers consistent data interfaces.
This reduces redundant development, lowers development costs, and builds an extensible device management architecture.

## Supported Function Modules

- ✅ Personnel Module
- ✅ Access Control Module
- ✅ Video Module
- ⏳ Vehicle Module
- ⏳ Visitor Module
- ⏳ Energy Consumption Module

## Project Structure

| Module Name                      | Description                                                                                                     |
|---------------------------------|-----------------------------------------------------------------------------------------------------------------|
| idevice-bootstrap               | Application startup module, containing the main application entry and configuration                               |
| idevice-common                  | Common module, providing shared utility classes, constants, and basic functions for the entire project            |
| idevice-components              | Component module, containing reusable function components independent of business logic                          |
| idevice-core                    | Core module, containing core business logic and domain models of the system                                       |
| idevice-feature                 | Feature module, providing unified business feature processing on top of the core module, such as command processing, device heartbeat, device data synchronization, etc. |
| idevice-web                     | Web module, handling HTTP requests, responses, and frontend interactions                                         |
| idevice-mcp                     | MCP protocol implementation, providing data interfaces for AI                                                    |

For detailed project structure and directory descriptions, please refer to the [Project Structure Documentation](doc/project-structure.md)

## Quick Start

### Requirements

- JDK 17+
- Maven 3.8.1+
- MySQL (optional)
- Redis (optional)

### Building the Source Code
#### Resolve the issue of the artemis-http-client package not being importable
```shell
mvn install:install-file -Dfile=$PATH_TO_PROJECT/lib/artemis-http-client-1.1.13.RELEASE.jar -DgroupId=com.hikvision.ga -DartifactId=artemis-http-client -Dversion=1.1.13.RELEASE -Dpackaging=jar
```

### API Documentation

After starting the application, access the API documentation at the following address:

- Knife4j UI: http://your-ip:port/doc.html

## Project Conventions

For detailed coding standards and project conventions, please refer to the [Coding Standards Documentation](doc/coding_standards_explanation.md)

## How to Integrate New Third-Party Platforms
1. According to the documents from the new platform, add a new SDK module in idevice-core-sdk
2. Use the SDK added in the previous step to implement the corresponding interfaces under idevice-core-module/concrete/
3. Configure the platform settings for the corresponding region through DeviceModuleConfigService

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Contact

- Email: jerrygeff@163.com 