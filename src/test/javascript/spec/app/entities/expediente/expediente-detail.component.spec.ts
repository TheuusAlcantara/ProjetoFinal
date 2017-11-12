/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExpedienteDetailComponent } from '../../../../../../main/webapp/app/entities/expediente/expediente-detail.component';
import { ExpedienteService } from '../../../../../../main/webapp/app/entities/expediente/expediente.service';
import { Expediente } from '../../../../../../main/webapp/app/entities/expediente/expediente.model';

describe('Component Tests', () => {

    describe('Expediente Management Detail Component', () => {
        let comp: ExpedienteDetailComponent;
        let fixture: ComponentFixture<ExpedienteDetailComponent>;
        let service: ExpedienteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [ExpedienteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExpedienteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExpedienteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExpedienteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExpedienteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Expediente(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.expediente).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
