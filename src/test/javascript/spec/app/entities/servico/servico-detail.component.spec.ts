/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServicoDetailComponent } from '../../../../../../main/webapp/app/entities/servico/servico-detail.component';
import { ServicoService } from '../../../../../../main/webapp/app/entities/servico/servico.service';
import { Servico } from '../../../../../../main/webapp/app/entities/servico/servico.model';

describe('Component Tests', () => {

    describe('Servico Management Detail Component', () => {
        let comp: ServicoDetailComponent;
        let fixture: ComponentFixture<ServicoDetailComponent>;
        let service: ServicoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [ServicoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServicoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ServicoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Servico(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.servico).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
