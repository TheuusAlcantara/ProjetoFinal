/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AgendamentoDetailComponent } from '../../../../../../main/webapp/app/entities/agendamento/agendamento-detail.component';
import { AgendamentoService } from '../../../../../../main/webapp/app/entities/agendamento/agendamento.service';
import { Agendamento } from '../../../../../../main/webapp/app/entities/agendamento/agendamento.model';

describe('Component Tests', () => {

    describe('Agendamento Management Detail Component', () => {
        let comp: AgendamentoDetailComponent;
        let fixture: ComponentFixture<AgendamentoDetailComponent>;
        let service: AgendamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [AgendamentoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AgendamentoService,
                    JhiEventManager
                ]
            }).overrideTemplate(AgendamentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgendamentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgendamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Agendamento(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.agendamento).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
