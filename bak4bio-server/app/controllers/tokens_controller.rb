#encoding: utf-8
class TokensController < ApplicationController
  skip_load_resource :only => [:create, :destroy, :check]
  skip_authorize_resource :only => [:create, :destroy, :check]
  skip_before_filter :verify_authenticity_token
  skip_before_filter :authenticate_user!, :only => [:create, :destroy, :check]
  
  respond_to :json
  
  # POST /tokens.json 
  def create
    email = params[:email]
    password = params[:password]
   
    if request.format != :json
      render :status=>406, :json=>{:message=>"The request must be json"}
      return
    end

    if email.nil? or password.nil?
      render :status=>400,
      :json=>{:message=>"E-mail and password is required."}
      return
    end

    @user = User.find_by_email(email.downcase)

    if @user.nil?
      logger.info("User #{email} failed signin, user cannot be found.")
      render :status=>401, :json=>{:message=>"E-mail and/or password invalid."}
      return
    end

    @user.ensure_authentication_token!

    if not @user.valid_password?(password)
      logger.info("User #{email} failed signin, password is invalid")
      render :status=>401, :json=>{:message=>"E-mail and/or password invalid."}
      return
    end

    if not @user.nil?
      render :status => 200, :json => {:auth_token => @user.authentication_token, :user_id => @user.id}
      return
    end
	
    render :status => 500, :json => {:message=>"User problem. Try again."}
  end

  # DELETE /tokens.json
  def destroy
    @user = User.find_by_authentication_token(params[:id])

    if @user.nil?
      logger.info("Token not found.")
      render :status=>404, :json=>{:message=>"Invalid token."}
    else
      @user.reset_authentication_token!
      render :status=>200, :json=>{:token=>params[:id]}
    end
  end
  
  # GET /tokens/1/check.json
  def check
    @user = User.find_by_authentication_token(params[:id])
  
    if @user.nil?
      render :status=>404, :json=>{:message=>"Invalid token."}
      return
    else
      render :status=>200, :json=>{:token=>params[:id]}
    end
  end
  
end