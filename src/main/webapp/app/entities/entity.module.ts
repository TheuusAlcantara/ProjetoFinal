import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SaafaCidadeModule } from './cidade/cidade.module';
import { SaafaEnderecoModule } from './endereco/endereco.module';
import { SaafaDepartamentoModule } from './departamento/departamento.module';
import { SaafaUBSModule } from './u-bs/ubs.module';
import { SaafaServicoModule } from './servico/servico.module';
import { SaafaFuncionarioModule } from './funcionario/funcionario.module';
import { SaafaExpedienteModule } from './expediente/expediente.module';
import { SaafaCargoModule } from './cargo/cargo.module';
import { SaafaAgendamentoModule } from './agendamento/agendamento.module';
import { SaafaFluxoAtendimentoModule } from './fluxo-atendimento/fluxo-atendimento.module';
import { SaafaAtividadeModule } from './atividade/atividade.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SaafaCidadeModule,
        SaafaEnderecoModule,
        SaafaDepartamentoModule,
        SaafaUBSModule,
        SaafaServicoModule,
        SaafaFuncionarioModule,
        SaafaExpedienteModule,
        SaafaCargoModule,
        SaafaAgendamentoModule,
        SaafaFluxoAtendimentoModule,
        SaafaAtividadeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaEntityModule {}
