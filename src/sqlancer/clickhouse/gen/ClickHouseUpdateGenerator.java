package sqlancer.clickhouse.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sqlancer.Randomly;
import sqlancer.clickhouse.ClickHouseProvider.ClickHouseGlobalState;
import sqlancer.clickhouse.ClickHouseSchema.ClickHouseColumn;
import sqlancer.clickhouse.ClickHouseSchema.ClickHouseTable;
import sqlancer.clickhouse.ast.constant.ClickHouseCreateConstant;
import sqlancer.clickhouse.ast.ClickHouseExpression;
import sqlancer.common.query.SQLQueryAdapter;

public class ClickHouseUpdateGenerator {

    public static SQLQueryAdapter getQuery(ClickHouseGlobalState globalState) {
        ClickHouseTable table = globalState.getSchema().getRandomTable();
        List<ClickHouseColumn> columns = table.getColumns();
        List<ClickHouseColumn> targetColumns = Randomly.nonEmptySubset(columns);
        List<ClickHouseExpression> values = new ArrayList<>();
        for (int i = 0; i < targetColumns.size(); i++) {
            values.add(ClickHouseCreateConstant.createInt32Constant(Randomly.getNotCachedInteger(0, 100)));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ");
        sb.append(table.getName());
        sb.append(" UPDATE ");
        List<String> updates = new ArrayList<>();
        for (int i = 0; i < targetColumns.size(); i++) {
            updates.add(targetColumns.get(i).getName() + " = " + values.get(i).toString());
        }
        sb.append(updates.stream().collect(Collectors.joining(", ")));
        sb.append(" WHERE ");
        ClickHouseExpressionGenerator gen = new ClickHouseExpressionGenerator(globalState);
        gen.setTablesAndColumns(globalState.getSchema().getRandomTableNonEmptyTables());
        ClickHouseExpression whereClause = gen.generateBooleanExpression();
        sb.append(whereClause.toString());
        return new SQLQueryAdapter(sb.toString());
    }
}
