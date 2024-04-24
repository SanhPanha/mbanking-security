### Project Structure 

```
src
├── main
│   ├── java
│   │   └── co
│   │       └── istad
│   │           └── mobilebankingcstad
│   │               ├── config
│   │               │   └── WebMvcConfiguration.java
│   │               ├── features
│   │               │   ├── files
│   │               │   │   ├── dto
│   │               │   │   │   └── FileResponse.java
│   │               │   │   ├── FileRestController.java
│   │               │   │   ├── FileServiceImpl.java
│   │               │   │   └── FileService.java
│   │               │   └── user
│   │               │       └── User.java
│   │               ├── MobileBankingCstadApplication.java
│   │               └── utils
│   │                   └── BaseResponse.java
│   └── resources
│       ├── application-dev.yaml
│       ├── application-prod.yaml
│       └── application.yaml
└── test
    └── java
        └── co
            └── istad
                └── mobilebankingcstad
                    └── MobileBankingCstadApplicationTests.java

17 directories, 12 files

```