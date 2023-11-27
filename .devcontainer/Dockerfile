FROM mcr.microsoft.com/devcontainers/base:jammy

USER vscode

RUN curl -s "https://get.sdkman.io" | bash
RUN chmod +x  "$HOME/.sdkman/bin/sdkman-init.sh"
RUN /bin/bash "$HOME/.sdkman/bin/sdkman-init.sh"

# RUN source    "$HOME/.sdkman/bin/sdkman-init.sh"
# RUN "sdk install scala 2.12.18"
# RUN "sdk install sbt"
# RUN "sdk install java 11.0.21-zulu"
