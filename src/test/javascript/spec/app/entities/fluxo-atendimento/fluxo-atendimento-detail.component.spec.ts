/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FluxoAtendimentoDetailComponent } from '../../../../../../main/webapp/app/entities/fluxo-atendimento/fluxo-atendimento-detail.component';
import { FluxoAtendimentoService } from '../../../../../../main/webapp/app/entities/fluxo-atendimento/fluxo-atendimento.service';
import { FluxoAtendimento } from '../../../../../../main/webapp/app/entities/fluxo-atendimento/fluxo-atendimento.model';

describe('Component Tests', () => {

    describe('FluxoAtendimento Management Detail Component', () => {
        let comp: FluxoAtendimentoDetailComponent;
        let fixture: ComponentFixture<FluxoAtendimentoDetailComponent>;
        let service: FluxoAtendimentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [FluxoAtendimentoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FluxoAtendimentoService,
                    JhiEventManager
                ]
            }).overrideTemplate(FluxoAtendimentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FluxoAtendimentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FluxoAtendimentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FluxoAtendimento(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fluxoAtendimento).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
