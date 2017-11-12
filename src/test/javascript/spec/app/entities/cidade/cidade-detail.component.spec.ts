/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CidadeDetailComponent } from '../../../../../../main/webapp/app/entities/cidade/cidade-detail.component';
import { CidadeService } from '../../../../../../main/webapp/app/entities/cidade/cidade.service';
import { Cidade } from '../../../../../../main/webapp/app/entities/cidade/cidade.model';

describe('Component Tests', () => {

    describe('Cidade Management Detail Component', () => {
        let comp: CidadeDetailComponent;
        let fixture: ComponentFixture<CidadeDetailComponent>;
        let service: CidadeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [CidadeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CidadeService,
                    JhiEventManager
                ]
            }).overrideTemplate(CidadeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CidadeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CidadeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cidade(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cidade).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
