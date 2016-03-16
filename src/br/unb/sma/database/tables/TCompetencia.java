/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TCompetenciaRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Mapeamento entre as Classes Processuais e os Orgaos Judicantes para onde
 * processos da classe podem ser distribuidos
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TCompetencia extends TableImpl<TCompetenciaRecord> {

    /**
     * The reference instance of <code>procjud.t_competencia</code>
     */
    public static final TCompetencia T_COMPETENCIA = new TCompetencia();
    private static final long serialVersionUID = 958496591;
    /**
     * The column <code>procjud.t_competencia.sig_classe</code>. Sigla identificadora da classe
     */
    public final TableField<TCompetenciaRecord, String> SIG_CLASSE = createField("sig_classe", org.jooq.impl.SQLDataType.VARCHAR.length(8).nullable(false), this, "Sigla identificadora da classe");
    /**
     * The column <code>procjud.t_competencia.sig_oj</code>.
     */
    public final TableField<TCompetenciaRecord, String> SIG_OJ = createField("sig_oj", org.jooq.impl.SQLDataType.VARCHAR.length(8).nullable(false), this, "");

    /**
     * Create a <code>procjud.t_competencia</code> table reference
     */
    public TCompetencia() {
        this("t_competencia", null);
    }

    /**
     * Create an aliased <code>procjud.t_competencia</code> table reference
     */
    public TCompetencia(String alias) {
        this(alias, T_COMPETENCIA);
    }

    private TCompetencia(String alias, Table<TCompetenciaRecord> aliased) {
        this(alias, aliased, null);
    }

    private TCompetencia(String alias, Table<TCompetenciaRecord> aliased, Field<?>[] parameters) {
        super(alias, Procjud.PROCJUD, aliased, parameters, "Mapeamento entre as Classes Processuais e os Orgaos Judicantes para onde processos da classe podem ser distribuidos");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TCompetenciaRecord> getRecordType() {
        return TCompetenciaRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TCompetenciaRecord> getPrimaryKey() {
        return Keys.COMPETENCIA_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TCompetenciaRecord>> getKeys() {
        return Arrays.<UniqueKey<TCompetenciaRecord>>asList(Keys.COMPETENCIA_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TCompetenciaRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TCompetenciaRecord, ?>>asList(Keys.T_COMPETENCIA__COMPETENCIA_FK1, Keys.T_COMPETENCIA__COMPETENCIA_FK2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TCompetencia as(String alias) {
        return new TCompetencia(alias, this);
    }

    /**
     * Rename this table
     */
    public TCompetencia rename(String name) {
        return new TCompetencia(name, null);
    }
}
