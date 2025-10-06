# Port and name
[ -z "$DB_PORT" ] && DB_PORT=9001
[ -z "$DB_NAME" ] && DB_NAME=turismoUyDB


if [ -z "$CATALINA_BASE" ]; then
  SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
  CATALINA_BASE=$(cd "$SCRIPT_DIR/.." && pwd)
fi

if [ -z "$DB_PATH" ]; then
  DB_PATH="$CATALINA_BASE/data/$DB_NAME"
fi

mkdir -p "$DB_PATH"

CATALINA_OPTS="$CATALINA_OPTS -Ddb.port=$DB_PORT -Ddb.name=$DB_NAME -Ddb.path=$DB_PATH"
export CATALINA_OPTS

echo "[setenv.sh] db.port=$DB_PORT"
echo "[setenv.sh] db.name=$DB_NAME"
echo "[setenv.sh] db.path=$DB_PATH"
echo "[setenv.sh] CATALINA_OPTS=$CATALINA_OPTS"