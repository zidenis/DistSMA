/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TDistribuidorRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Responsavel pela realizacao de uma distribuicao de processo
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TDistribuidor extends TableImpl<TDistribuidorRecord> {

    /**
     * The reference instance of <code>procjud.t_distribuidor</code>
     */
    public static final TDistribuidor T_DISTRIBUIDOR = new TDistribuidor();
    private static final long serialVersionUID = 592371027;
    /**
     * The column <code>procjud.t_distribuidor.cod_distribuidor</code>. Codigo que identifica o distribuidor do processo
     */
    public final TableField<TDistribuidorRecord, String> COD_DISTRIBUIDOR = createField("cod_distribuidor", org.jooq.impl.SQLDataType.VARCHAR.length(8).nullable(false), this, "Codigo que identifica o distribuidor do processo");
    /**
     * The column <code>procjud.t_distribuidor.nom_distribuidor</code>. Nome do distribuidor
     */
    public final TableField<TDistribuidorRecord, String> NOM_DISTRIBUIDOR = createField("nom_distribuidor", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Nome do distribuidor");

    /**
     * Create a <code>procjud.t_distribuidor</code> table reference
     */
    public TDistribuidor() {
        this("t_distribuidor", null);
    }

    /**
     * Create an aliased <code>procjud.t_distribuidor</code> table reference
     */
    public TDistribuidor(String alias) {
        this(alias, T_DISTRIBUIDOR);
    }

    private TDistribuidor(String alias, Table<TDistribuidorRecord> aliased) {
        this(alias, aliased, null);
    }

    private TDistribuidor(String alias, Table<TDistribuidorRecord> aliased, Field<?>[] parameters) {
        super(alias, Procjud.PROCJUD, aliased, parameters, "Responsavel pela realizacao de uma distribuicao de processo");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TDistribuidorRecord> getRecordType() {
        return TDistribuidorRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TDistribuidorRecord> getPrimaryKey() {
        return Keys.DISTRIBUIDOR_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TDistribuidorRecord>> getKeys() {
        return Arrays.<UniqueKey<TDistribuidorRecord>>asList(Keys.DISTRIBUIDOR_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TDistribuidor as(String alias) {
        return new TDistribuidor(alias, this);
    }

    /**
     * Rename this table
     */
    public TDistribuidor rename(String name) {
        return new TDistribuidor(name, null);
    }
}
