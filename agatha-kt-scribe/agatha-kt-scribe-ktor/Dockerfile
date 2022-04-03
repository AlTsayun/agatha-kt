FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/install/agatha-kt-scribe/ /app/
WORKDIR /app/bin
CMD ["./agatha-kt-scribe"]