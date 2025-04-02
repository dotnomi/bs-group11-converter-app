FROM ubuntu:22.10

ENV JAVA_HOME=/opt/java
ENV PATH=${JAVA_HOME}/bin:$PATH

RUN \
    echo "installing java environment"\
    && FEATURE_VERSION=21 \
    && echo "JDK Version : ${FEATURE_VERSION}" \
    && API_URL="https://api.adoptium.net/v3/binary/latest/${FEATURE_VERSION}/ga/${OS}/${ARCH}/${IMAGE_TYPE}/hotspot/normal/eclipse" \
    && echo "Downloading from ${API_URL}" \