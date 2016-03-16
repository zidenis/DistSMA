/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TTipoDistRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Tipos de Distribuicao
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TTipoDist extends TableImpl<TTipoDistRecord> {

    /**
     * The reference instance of <code>procjud.t_tipo_dist</code>
     */
    public static final TTipoDist T_TIPO_DIST = new TTipoDist();
    private static final long serialVersionUID = -1488904001;
    /**
     * The column <code>procjud.t_tipo_dist.cod_tipo_dist</code>. Codigo do tipo de distribuicao
     */
    public final TableField<TTipoDistRecord, String> COD_TIPO_DIST = createField("cod_tipo_dist", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "Codigo do tipo de distribuicao");
    /**
     * The column <code>procjud.t_tipo_dist.des_tipo_dist</code>. Descricao do tipo de distribuicao
     */
    public final TableField<TTipoDistRecord, String> DES_TIPO_DIST = createField("des_tipo_dist", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Descricao do tipo de distribuicao");

    /**
     * Create a <code>procjud.t_tipo_dist</code> table reference
     */
    public TTipoDist() {
        this("t_tipo_dist", null);
    }

    /**
     * Create an aliased <code>procjud.t_tipo_dist</code> table reference
     */
    public TTipoDist(String alias) {
        this(alias, T_TIPO_DIST);
    }

    private TTipoDist(String alias, Table<TTipoDistRecord> aliased) {
        this(alias, aliased, null);
    }

    private TTipoDist(String alias, Table<TTipoDistRecord> aliased, Field<?>[] parameters) {
        super(alias, Procjud.PROCJUD, aliased, parameters, "Tipos de Distribuicao");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TTipoDistRecord> getRecordType() {
        return TTipoDistRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TTipoDistRecord> getPrimaryKey() {
        return Keys.TIPO_DIST_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TTipoDistRecord>> getKeys() {
        return Arrays.<UniqueKey<TTipoDistRecord>>asList(Keys.TIPO_DIST_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TTipoDist as(String alias) {
        return new TTipoDist(alias, this);
    }

    /**
     * Rename this table
     */
    public TTipoDist rename(String name) {
        return new TTipoDist(name, null);
    }
}
