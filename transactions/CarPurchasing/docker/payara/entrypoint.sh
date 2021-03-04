#!/bin/bash

# Run init scripts (credits go to MySQL Docker entrypoint script)

until curl mysqlcar:3306; do
  >&2 echo "MySQLcar is unavailable - sleeping"
  sleep 1
done

until curl mysqlpurchaser:3306; do
  >&2 echo "MySQLpurchaser is unavailable - sleeping"
  sleep 1
done

for f in ${SCRIPT_DIR}/init_* ${SCRIPT_DIR}/init.d/*; do
      case "$f" in
        *.sh)  echo "[Entrypoint] running $f"; . "$f" ;;
        *)     echo "[Entrypoint] ignoring $f" ;;
      esac
      echo
done

exec ${SCRIPT_DIR}/startInForeground.sh

