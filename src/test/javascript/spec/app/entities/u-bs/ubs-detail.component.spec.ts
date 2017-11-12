/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SaafaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UBSDetailComponent } from '../../../../../../main/webapp/app/entities/u-bs/ubs-detail.component';
import { UBSService } from '../../../../../../main/webapp/app/entities/u-bs/ubs.service';
import { UBS } from '../../../../../../main/webapp/app/entities/u-bs/ubs.model';

describe('Component Tests', () => {

    describe('UBS Management Detail Component', () => {
        let comp: UBSDetailComponent;
        let fixture: ComponentFixture<UBSDetailComponent>;
        let service: UBSService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SaafaTestModule],
                declarations: [UBSDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UBSService,
                    JhiEventManager
                ]
            }).overrideTemplate(UBSDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UBSDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UBSService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UBS(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.uBS).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
