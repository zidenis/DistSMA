/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TOrgaoJudicanteRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Entidade diretamente responsavel por conduzir e decidir um determinado
 * processo judicial
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TOrgaoJudicante extends TableImpl<TOrgaoJudicanteRecord> {

    /**
     * The reference instance of <code>procjud.t_orgao_judicante</code>
     */
    public static final TOrgaoJudicante T_ORGAO_JUDICANTE = new TOrgaoJudicante();
    private static final long serialVersionUID = -294277746;
    /**
     * The column <code>procjud.t_orgao_judicante.sig_oj</code>. Sigla identificadora do Orgao Judicante
     */
    public final TableField<TOrgaoJudicanteRecord, String> SIG_OJ = createField("sig_oj", org.jooq.impl.SQLDataType.VARCHAR.length(8).nullable(false), this, "Sigla identificadora do Orgao Judicante");
    /**
     * The column <code>procjud.t_orgao_judicante.des_oj</code>. Descricao do Orgao Judicante
     */
    public final TableField<TOrgaoJudicanteRecord, String> DES_OJ = createField("des_oj", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Descricao do Orgao Judicante");

    /**
     * Create a <code>procjud.t_orgao_judicante</code> table reference
     */
    public TOrgaoJudicante() {
        this("t_orgao_judicante", null);
    }

    /**
     * Create an aliased <code>procjud.t_orgao_judicante</code> table reference
     */
    public TOrgaoJudicante(String alias) {
        this(alias, T_ORGAO_JUDICANTE);
    }

    private TOrgaoJudicante(String alias, Table<TOrgaoJudicanteRecord> aliased) {
        this(alias, aliased, null);
    }

    private TOrgaoJudicante(String alias, Table<TOrgaoJudicanteRecord> aliased, Field<?>[] parameters) {
        super(alias, Procjud.PROCJUD, aliased, parameters, "Entidade diretamente responsavel por conduzir e decidir um determinado processo judicial");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TOrgaoJudicanteRecord> getRecordType() {
        return TOrgaoJudicanteRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TOrgaoJudicanteRecord> getPrimaryKey() {
        return Keys.ORGAO_JUDICANTE_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TOrgaoJudicanteRecord>> getKeys() {
        return Arrays.<UniqueKey<TOrgaoJudicanteRecord>>asList(Keys.ORGAO_JUDICANTE_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TOrgaoJudicante as(String alias) {
        return new TOrgaoJudicante(alias, this);
    }

    /**
     * Rename this table
     */
    public TOrgaoJudicante rename(String name) {
        return new TOrgaoJudicante(name, null);
    }
}
