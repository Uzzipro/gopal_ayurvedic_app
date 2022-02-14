package com.app.gopalayurvediccenter.Service;

import com.app.gopalayurvediccenter.Dataclass.OrderCreatedResponseDto;
import com.app.gopalayurvediccenter.Dataclass.RazorPayCreateOrderDtoBody;
import com.app.gopalayurvediccenter.Utils.Constants;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST(Constants.RP_ORDERS)
    Call<OrderCreatedResponseDto> sendFeedback(@Body RazorPayCreateOrderDtoBody razorPayCreateOrderDtoBody);
}
