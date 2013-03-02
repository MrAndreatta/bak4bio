class ApplicationController < ActionController::Base
  protect_from_forgery
  
  load_resource
  authorize_resource
 
  before_filter :authenticate_user!
  before_filter :set_current_user
  
  def set_current_user   
    User.current_user = current_user
  end
  
  rescue_from CanCan::AccessDenied do |exception|
    Rails.logger.debug "User not authorized #{exception.action} #{exception.subject.inspect}"
    respond_to do |format|
      format.html { render "#{Rails.root}/public/403", formats: [:html], status: 403, layout: false }
      format.json { render json: [{:action => exception.action, :entity => exception.subject.inspect}], :status => :forbidden  }
    end
  end
  
end
