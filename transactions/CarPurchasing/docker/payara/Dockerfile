FROM payara/server-full:5.184

COPY --chown=payara:payara ./docker/payara/commands.asadmin $POSTBOOT_COMMANDS

COPY --chown=payara:payara ./docker/payara/entrypoint.sh ./scripts

RUN chmod +rx ./scripts/entrypoint.sh

COPY --chown=payara:payara ./docker/mysql-connector-java-8.0.21.jar ./appserver/bin

COPY --chown=payara:payara ./target/CarPurchasing-1.war $DEPLOY_DIR

# RUN mkdir -p ./appserver/mysql/bin

# COPY --chown=payara:payara mysql-connector-java-8.0.21.jar ./appserver/bin

# CMD ./appserver/bin/asadmin add-library ./appserver/bin/mysql-connector-java-8.0.21.jar

# COPY domain.xml ./appserver/glassfish/domains/production/config/
# COPY init_2_bd.sh ./scripts

# WORKDIR ${PAYARA_PATH}/bin

# CMD ./appserver/bin/asadmin add-library mysql-connector-java-8.0.21.jar

# RUN ./appserver/bin/asadmin create-jdbc-connection-pool --driverclassname com.mysql.cj.jdbc.Driver --restype java.sql.Driver \
# --property user=root:password=root:url="jdbc\:mysql\://localhost\:3376/sandbox?useSSL\\=false&serverTimezone\\=Europe/Moscow" my_persistence_unit

# RUN ./appserver/bin/asadmin create-jdbc-resource --connectionpoolid my_persistence_unit jdbc/my_persistence_unit