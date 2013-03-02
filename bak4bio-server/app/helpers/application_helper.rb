module ApplicationHelper
  
  def current_entity_errors(controller_name)
    if controller_name == :contents.to_s
      return @content ? @content.errors.full_messages : []
    end
    
    if controller_name == :blasts.to_s
      return @blast ? @blast.errors.full_messages : []
    end
    
    return []
  end
end
