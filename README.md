[![License](https://img.shields.io/badge/License-Apache_2.0-green.svg)](https://opensource.org/licenses/Apache-2.0)
[![Release](https://img.shields.io/github/tag/the-flx/flx-java.svg?label=release&logo=github)](https://github.com/the-flx/flx-java/releases/latest)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.the-flx/flx-java?logo=sonatype)](https://central.sonatype.com/artifact/io.github.the-flx/flx-java)

# flx-java
> Rewrite emacs-flx in Java

[![CI](https://github.com/the-flx/flx-java/actions/workflows/test.yml/badge.svg)](https://github.com/the-flx/flx-java/actions/workflows/test.yml)

## 🔨 Usage

```java
Result result = Flx.score("switch-to-buffer", "stb");
System.out.println(result.score);  // 237
```

## 🛠️ Development

For testing, we use [IntelliJ][]'s built-in testing library, [JUnit][].

See Microsoft's support page for more information: [Walkthrough: Create and run unit tests for managed code](https://learn.microsoft.com/en-us/visualstudio/test/walkthrough-creating-and-running-unit-tests-for-managed-code).

## 🔗 Links

- [A Step-by-Step Guide to Publishing Your Maven Project to Maven Central Repository](https://medium.com/@hydrurdgn/a-step-by-step-guide-to-publishing-your-maven-project-to-maven-central-repository-7a8928b5e5f5)
- [Publishing By Using the Maven Plugin](https://central.sonatype.org/publish/publish-portal-maven/#wait-for-publishing)

## ⚜ License

Copyright 2024-present Jen-Chieh Shen.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

See [`LICENSE`](./LICENSE) for details.


<!-- Links -->

[Intellij]: https://www.jetbrains.com/idea/
[JUint]: https://junit.org/
